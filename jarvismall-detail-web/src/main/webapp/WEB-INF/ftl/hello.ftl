<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>test freemaker </title>
</head>
<body>
<a href="http://www.baidu.com">测试${hello}网站</a>

<table>
<#list list as item>
    <#if item_index%2==0>
    <tr bgcolor="#663399">
    <#else >
    <tr bgcolor="#a52a2a">
    </#if>
    <td>${item}</td>
    </tr>
</#list>
</table>

日期:${dateKey?date} : ${dateKey?time}
日期:${dateKey?datetime}
customTime: ${dateKey?string("yyyy年MM月dd日 HH时:mm分:ss秒")}
<br />
null:${val!"default"}
<br />
<#include "base.ftl">
</body>
</html>
