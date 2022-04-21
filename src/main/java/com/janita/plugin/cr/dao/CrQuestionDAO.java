package com.janita.plugin.cr.dao;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;

import java.sql.Connection;
import java.util.List;

/**
 * CrQuestionDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionDAO extends BaseDAO<CrQuestion> {

    private final Connection connection = SqliteDatabaseServiceImpl.getInstance().getConnection();

    public void batchInsert(List<CrQuestion> questionList) {
        if (questionList == null || questionList.size() == 0) {
            return;
        }

        String sql = "INSERT INTO cr_question (id, project_name, file_path, file_name, \n"
                + "                         language, type, level, state, \n"
                + "                         assign_from, assign_to, question_code, better_code, \n"
                + "                         description, create_git_branch_name, solve_git_branch_name, create_time, \n"
                + "                         solve_time,offset_start, offset_end)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int size = questionList.size();
        if (size == 1) {
            CrQuestion note = questionList.get(0);
            update(connection, sql,
                    note.getId(), note.getProjectName(), note.getFilePath(), note.getFileName(),
                    note.getLanguage(), note.getType(), note.getLevel(), note.getState(),
                    note.getAssignFrom(), note.getAssignTo(), note.getQuestionCode(), note.getBetterCode(),
                    note.getDescription(), note.getCreateGitBranchName(), note.getSolveGitBranchName(), note.getCreateTime(),
                    note.getSolveTime(), note.getOffsetStart(), note.getOffsetEnd());
        } else {
            Object[][] args = new Object[size][12];
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
            updateBatch(connection, sql, args);
        }
    }

    public void batchUpdate(List<CrQuestion> questionList) {
    }

    public void batchDelete(List<Integer> questionIdList) {
    }

    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        return null;
    }
}
