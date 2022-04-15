<#list questionList as question>
>###<font color = 'aqua'>${question_index + 1}.${question.projectName}-${question.gitBranchName}-${question.projectName}-${question.className}-${question.lineFrom}到${question.lineTo}</font>
>####问题类型
>><font color = 'black'>${question.type}</font>
>####指派给
>><font color = 'black'>${question.toAccount}</font>
>####级别
>><font color = 'black'>${question.level}</font>
>####状态
>>${question.state}
>####问题代码
>>```java
${question.questionCode}
>>```
>####建议写法
>>```java
${question.betterCode}
>>```
>####描述
>>```java
${question.desc}
>>```
<table bgcolor="#ffebcd" height="20"></table>

</#list>