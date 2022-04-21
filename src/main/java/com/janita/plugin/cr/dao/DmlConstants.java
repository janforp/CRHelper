package com.janita.plugin.cr.dao;

import lombok.experimental.UtilityClass;

/**
 * DmlConstants
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class DmlConstants {

    public final String LAST_INSERT_ROW_ID = "SELECT last_insert_rowid() id";

    public final String INSERT_SQL = "INSERT INTO cr_question (id, project_name, file_path, file_name, \n"
            + "                         language, type, level, state, \n"
            + "                         assign_from, assign_to, question_code, better_code, \n"
            + "                         description, create_git_branch_name, solve_git_branch_name, create_time, \n"
            + "                         solve_time,offset_start, offset_end)\n"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public final String UPDATE_SQL = "update cr_question\n"
            + "set project_name=?,\n"
            + "    file_path=?,\n"
            + "    file_name=?,\n"
            + "    language=?,\n"
            + "    type=?,\n"
            + "    level=?,\n"
            + "    state=?,\n"
            + "    assign_from=?,\n"
            + "    assign_to=?,\n"
            + "    question_code=?,\n"
            + "    better_code=?,\n"
            + "    description=?,\n"
            + "    create_git_branch_name=?,\n"
            + "    solve_git_branch_name=?,\n"
            + "    solve_time = ?,\n"
            + "    offset_start = ?,\n"
            + "    offset_end = ?\n"
            + "where id = ?";

    public final String DELETE_SQL = "UPDATE cr_question\n"
            + "SET is_delete = id\n"
            + "WHERE  id = ?";

    public final String QUERY_SAL = "SELECT * FROM cr_question WHERE project_name IN (?) AND state IN (?) AND is_delete = 0 ORDER BY create_time DESC";
}