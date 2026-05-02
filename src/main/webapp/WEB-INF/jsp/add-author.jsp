<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Add/Edit Author</title></head>
<body>
    <h1>Add/Edit Author</h1>
    <a href="/authors">Back to Authors</a>
    <form:form action="/add-author" modelAttribute="author" method="post">
        <form:hidden path="id"/>
        <p>Name: <form:input path="name"/> <form:errors path="name" cssStyle="color:red"/></p>
        <p>Nationality: <form:input path="nationality"/> <form:errors path="nationality" cssStyle="color:red"/></p>
        <p><input type="submit" value="Save"/></p>
    </form:form>
</body>
</html>
