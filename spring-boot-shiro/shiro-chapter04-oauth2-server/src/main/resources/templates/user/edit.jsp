
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:form="http://www.springframework.org/tags/form">
<head>
    <title></title>
    <link rel="stylesheet" href="/css/css.css">
    <link rel="stylesheet" href="/JQuery zTree v3.5.15/css/zTreeStyle/zTreeStyle.css">
    <style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>

</head>
<body>

    <form:form method="post" commandName="user">
        <form:hidden path="id"/>
        <form:hidden path="salt"/>
        <c:if test="${op ne '新增'}">
            <form:hidden path="password"/>
        </c:if>

        <div class="form-group">
            <form:label path="username">用户名：</form:label>
            <form:input path="username"/>
        </div>

        <c:if test="${op eq '新增'}">
            <div class="form-group">
                <form:label path="password">密码：</form:label>
                <form:password path="password"/>
            </div>
        </c:if>

        <form:button>${op}</form:button>

    </form:form>

</body>
</html>