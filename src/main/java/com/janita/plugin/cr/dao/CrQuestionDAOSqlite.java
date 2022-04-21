package com.janita.plugin.cr.dao;

import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * CrQuestionDAO
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("unused")
public class CrQuestionDAOSqlite extends BaseDAO<CrQuestion> implements ICrQuestionDAO {

    private static final IDatabaseService SQLITE_DATA = SqliteDatabaseServiceImpl.getInstance();

    public static CrQuestionDAOSqlite getInstance() {
        return new CrQuestionDAOSqlite();
    }

    public void insert(CrQuestion question) {
        Connection connection = SQLITE_DATA.getConnection();
        try {
            update(connection, DmlConstants.INSERT_SQL,
                    question.getId(), question.getProjectName(), question.getFilePath(), question.getFileName(),
                    question.getLanguage(), question.getType(), question.getLevel(), question.getState(),
                    question.getAssignFrom(), question.getAssignTo(), question.getQuestionCode(), question.getBetterCode(),
                    question.getDescription(), question.getCreateGitBranchName(), question.getSolveGitBranchName(), question.getCreateTime(),
                    question.getSolveTime(), question.getOffsetStart(), question.getOffsetEnd());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLITE_DATA.closeResource(connection, null, null);
        }
    }

    @Override
    public void update(CrQuestion note) {
        Connection connection = SQLITE_DATA.getConnection();
        try {
            update(connection, DmlConstants.UPDATE_SQL,
                    note.getProjectName(), note.getFilePath(), note.getFileName(), note.getLanguage(),
                    note.getType(), note.getLevel(), note.getState(), note.getAssignFrom(),
                    note.getAssignTo(), note.getQuestionCode(), note.getBetterCode(), note.getDescription(),
                    note.getCreateGitBranchName(), note.getSolveGitBranchName(), note.getSolveTime(), note.getOffsetStart(),
                    note.getOffsetEnd(), note.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLITE_DATA.closeResource(connection, null, null);
        }
    }

    @Override
    public void batchDelete(List<Integer> questionIdList) {
        if (CollectionUtils.isEmpty(questionIdList)) {
            return;
        }
        Connection connection = SQLITE_DATA.getConnection();
        if (questionIdList.size() == 1) {
            update(connection, DmlConstants.DELETE_SQL, questionIdList.get(0));
            return;
        }
        int size = questionIdList.size();
        Object[][] args = new Object[size][1];
        for (int i = 0; i < questionIdList.size(); i++) {
            Integer id = questionIdList.get(i);
            args[i][0] = id;
        }
        updateBatch(connection, DmlConstants.DELETE_SQL, args);
    }

    @Override
    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        Set<String> projectNameSet = request.getProjectNameSet();
        Set<CrQuestionState> stateSet = request.getStateSet();
        Connection connection = SQLITE_DATA.getConnection();
        try {
            return queryList(connection, DmlConstants.QUERY_SAL, StringUtils.join(projectNameSet, ","), StringUtils.join(stateSet, ","));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
