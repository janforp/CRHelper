<#list questionList as question>
#${question_index + 1}.${question.projectName}-${question.gitBranchName}-${question.projectName}-${question.className}-${question.lineFrom}到${question.lineTo}
##问题类型
${question.type}
##指派给
${question.toAccount}
##级别
${question.level}
##状态
${question.state}
##问题代码
```
${question.questionCode}
```
##建议写法
```
${question.betterCode}
```
##描述
${question.desc}
</#list>