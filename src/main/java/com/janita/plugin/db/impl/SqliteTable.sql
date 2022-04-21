create table IF NOT EXISTS cr_question
(
    id
    INTEGER
    AUTOINCREMENT
    PRIMARY
    KEY,
    project_name
    TEXT,
    file_path
    TEXT,
    file_name
    TEXT,
    language
    TEXT,
    type
    TEXT,
    level
    TEXT,
    state
    TEXT,
    assign_from
    TEXT,
    assign_to
    TEXT,
    question_code
    TEXT,
    better_code
    TEXT,
    description
    TEXT,
    create_git_branch_name
    TEXT,
    solve_git_branch_name
    TEXT,
    create_time
    TEXT,
    solve_time
    TEXT,
    offset_start
    INTEGER ,
    offset_end
    INTEGER,
);



INSERT INTO cr_question (id, project_name, file_path, file_name,
                         language, type, level, state,
                         assign_from, assign_to, question_code, better_code,
                         description, create_git_branch_name, solve_git_branch_name, create_time,
                         solve_time,offset_start, offset_end,)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)