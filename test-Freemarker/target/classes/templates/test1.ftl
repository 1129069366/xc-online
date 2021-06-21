<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title></head>
<body>
    Hello ${name}!

<br>
    <table>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>金额</td>
<#--<!--            <td>出生日期</td>&ndash;&gt;-->
        </tr>
        <#if stus?? >
            <#list stus as stu>
                <tr >
                    <td>${stu_index+1}</td>
                    <td>${(stu.name)!''}</td>
                    <td>${(stu.age!)!''}</td>
                    <td>${(stu.money)!''}</td>
                    <#--${stu.birthday}-->
                </tr>
            </#list>
        </#if>
    </table><br/>
    遍历输出两个学生信息：<br/>
    <table>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>钱包</td>
        </tr>
        <#list stuMap?keys as k>
            <tr>
                <td>${k_index+1}</td>
                <td>${(stuMap[k].name)!''}</td>
                <td>${stuMap[k].age}</td>
                <td>${stuMap[k].money}</td>
            </tr>
        </#list>
    </table>

</body>
</html>