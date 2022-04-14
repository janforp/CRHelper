###<font color = 'aqua'>1.CRHelper-master-CRHelper-MDFreeMarkProcessor.java-9到14</font>
>####问题类型
>>建议
>####指派给
>>王尚飞
>####级别
>>1
>####状态
>>未解决
>####问题代码
```
 Configuration configuration = new Configuration();
        String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("MDTemplate", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        return configuration.getTemplate("MDTemplate");
```

>####建议写法
```
 Configuration configuration = new Configuration();
        String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("MDTemplate", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        return configuration.getTemplate("MDTemplate");
```
>####描述
>>当时冯绍峰

###<font color = 'aqua'>2.CRHelper-master-CRHelper-MDFreeMarkProcessor.java-9到14</font>
>####问题类型
>>建议
>####指派给
>>王尚飞
>####级别
>>1
>####状态
>>未解决
>####问题代码
```
 Configuration configuration = new Configuration();
        String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("MDTemplate", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        return configuration.getTemplate("MDTemplate");
```

>####建议写法
```
 Configuration configuration = new Configuration();
        String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("MDTemplate", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        return configuration.getTemplate("MDTemplate");
```
>####描述
>>当时冯绍峰