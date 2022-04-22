package com.janita.plugin.cr.dao.impl;

import com.janita.plugin.common.constant.DmlConstants;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.cr.dao.BaseDAO;
import com.janita.plugin.cr.dao.ICrQuestionDAO;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.db.DatabaseServiceFactory;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrQuestionDAO
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("unused")
public class CrQuestionSqliteDAO extends BaseDAO<CrQuestion> implements ICrQuestionDAO {

    public static CrQuestionSqliteDAO getInstance() {
        return new CrQuestionSqliteDAO();
    }

    public boolean insert(CrQuestion question) {
        IDatabaseService database = DatabaseServiceFactory.getDatabase();
        Connection connection = database.getConnection();
        try {
            boolean success = update(connection, DmlConstants.INSERT_SQL,
                    question.getId(), question.getProjectName(), question.getFilePath(), question.getFileName(),
                    question.getLanguage(), question.getType(), question.getLevel(), question.getState(),
                    question.getAssignFrom(), question.getAssignTo(), question.getQuestionCode(), question.getBetterCode(),
                    question.getDescription(), question.getCreateGitBranchName(), question.getSolveGitBranchName(), question.getCreateTime(),
                    question.getSolveTime(), question.getOffsetStart(), question.getOffsetEnd());
            if (!success) {
                return false;
            }
            Pair<Boolean, Integer> pair = getLastInsertId(connection, database instanceof SqliteDatabaseServiceImpl ? DmlConstants.LAST_INSERT_ROW_ID_OF_SQLITE : DmlConstants.LAST_INSERT_ROW_ID_OF_MYSQL);
            if (BooleanUtils.isNotTrue(pair.getLeft())) {
                return false;
            }
            question.setId(pair.getRight());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Pair<Boolean, Integer> getLastInsertId(Connection connection, String sql) {
        Pair<Boolean, Object> pair = getValue(connection, sql);
        if (BooleanUtils.isNotTrue(pair.getLeft())) {
            return Pair.of(false, null);
        }
        Object right = pair.getRight();
        if (right instanceof BigInteger) {
            BigInteger id = (BigInteger) right;
            return Pair.of(true, id.intValue());
        }
        return Pair.of(true, (Integer) right);
    }

    @Override
    public boolean update(CrQuestion note) {
        IDatabaseService database = DatabaseServiceFactory.getDatabase();
        Connection connection = database.getConnection();
        try {
            return update(connection, DmlConstants.UPDATE_SQL,
                    note.getProjectName(), note.getFilePath(), note.getFileName(), note.getLanguage(),
                    note.getType(), note.getLevel(), note.getState(), note.getAssignFrom(),
                    note.getAssignTo(), note.getQuestionCode(), note.getBetterCode(), note.getDescription(),
                    note.getCreateGitBranchName(), note.getSolveGitBranchName(), note.getSolveTime(), note.getOffsetStart(),
                    note.getOffsetEnd(), note.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean batchDelete(List<Integer> questionIdList) {
        if (CollectionUtils.isEmpty(questionIdList)) {
            return true;
        }
        IDatabaseService database = DatabaseServiceFactory.getDatabase();
        Connection connection = database.getConnection();
        if (questionIdList.size() == 1) {
            return update(connection, DmlConstants.DELETE_SQL, questionIdList.get(0));
        }
        int size = questionIdList.size();
        Object[][] args = new Object[size][1];
        for (int i = 0; i < questionIdList.size(); i++) {
            Integer id = questionIdList.get(i);
            args[i][0] = id;
        }
        return updateBatch(connection, DmlConstants.DELETE_SQL, args);
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        Set<CrQuestionState> stateSet = request.getStateSet();
        IDatabaseService database = DatabaseServiceFactory.getDatabase();
        Connection connection = database.getConnection();
        try {
            List<CrQuestion> questionList = queryList(connection, DmlConstants.QUERY_SAL, request.getProjectName());
            if (CollectionUtils.isEmpty(questionList)) {
                return Pair.of(true, new ArrayList<>(0));
            }
            if (CollectionUtils.isEmpty(stateSet)) {
                return Pair.of(true, questionList);

            }
            List<CrQuestion> list = questionList.stream().filter(item -> stateSet.contains(CrQuestionState.getByDesc(item.getState()))).collect(Collectors.toList());
            return Pair.of(true, list);
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, new ArrayList<>(0));
        }
    }

    private void doInsert(Connection connection, List<CrQuestion> questionList) {
        if (questionList == null || questionList.size() == 0) {
            return;
        }
        int size = questionList.size();
        Object[][] args = new Object[size][19];
        for (int i = 0; i < size; i++) {
            CrQuestion note = questionList.get(i);
            args[i][0] = note.getId();
            args[i][1] = note.getProjectName();
            args[i][2] = note.getFilePath();
            args[i][3] = note.getFileName();

            args[i][4] = note.getLanguage();
            args[i][5] = note.getType();
            args[i][6] = note.getLevel();
            args[i][7] = note.getState();

            args[i][8] = note.getAssignFrom();
            args[i][9] = note.getAssignTo();
            args[i][10] = note.getQuestionCode();
            args[i][11] = note.getBetterCode();

            args[i][12] = note.getDescription();
            args[i][13] = note.getCreateGitBranchName();
            args[i][14] = note.getSolveGitBranchName();
            args[i][15] = note.getCreateTime();

            args[i][16] = note.getSolveTime();
            args[i][17] = note.getOffsetStart();
            args[i][18] = note.getOffsetEnd();
        }
        updateBatch(connection, DmlConstants.INSERT_SQL, args);
    }

    public void doBatchUpdate(Connection connection, List<CrQuestion> questionList) {
        String sql = DmlConstants.UPDATE_SQL;
        int size = questionList.size();
        Object[][] args = new Object[size][19];
        for (int i = 0; i < size; i++) {
            CrQuestion note = questionList.get(i);
            args[i][0] = note.getProjectName();
            args[i][1] = note.getFilePath();
            args[i][2] = note.getFileName();
            args[i][3] = note.getLanguage();
            args[i][4] = note.getCreateTime();
            args[i][5] = note.getType();
            args[i][6] = note.getLevel();
            args[i][7] = note.getDescription();
            args[i][8] = note.getState();
            args[i][9] = note.getAssignFrom();
            args[i][10] = note.getAssignTo();
            args[i][11] = note.getQuestionCode();
            args[i][12] = note.getBetterCode();

            args[i][13] = note.getDescription();
            args[i][14] = note.getCreateGitBranchName();
            args[i][15] = note.getSolveGitBranchName();
            args[i][16] = note.getSolveTime();
            args[i][17] = note.getOffsetStart();
            args[i][18] = note.getOffsetEnd();

            args[i][19] = note.getId();
        }
        updateBatch(connection, sql, args);
    }
}
