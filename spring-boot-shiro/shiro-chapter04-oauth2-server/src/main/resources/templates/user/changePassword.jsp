<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title></title>
    <link rel="stylesheet" href="/css/css.css">
</head>
<body>

    <form method="post">
        <div class="form-group">
            <label for="newPassword">新密码：</label>
            <input type="text" id="newPassword" name="newPassword"/>
        </div>
        <input type="submit" th:value="${op}">
    </form>

</body>
</html>