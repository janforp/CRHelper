package com.janita.plugin.cr.remote;

import com.janita.plugin.cr.domain.CrQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * QuestionRemote
 * TODO
 *
 * @author zhucj
 * @since 20220324
 */
public class QuestionRemote {

    private static final AtomicLong id = new AtomicLong(2);

    public static void add(CrQuestion question) {
        question.setId(id.incrementAndGet());
    }

    public static void update(CrQuestion question) {

    }

    public static List<CrQuestion> query(CrQuestionQueryRequest request) {
        Set<String> projectNameSet = request.getProjectNameSet();
        String projectName = null;
        if (projectNameSet != null && projectNameSet.size() > 0) {
            projectName = new ArrayList<>(projectNameSet).get(0);
        }

        CrQuestion question = new CrQuestion();
        question.setId(1L);
        question.setLevel("1");
        question.setProjectName(projectName);
        question.setType("性能");
        question.setLineFrom(1);
        question.setLineTo(2);
        question.setClassName("HashMap.java");
        question.setQuestionCode("import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import java.util.Set;");
        question.setBetterCode("import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import java.util.Set;");
        question.setDesc("描述一下");
        question.setCreateTime("2022-2-12 12:12:12");
        question.setSolveTime("2022-12-12 12:12:12");
        return Collections.singletonList(question);
    }
}