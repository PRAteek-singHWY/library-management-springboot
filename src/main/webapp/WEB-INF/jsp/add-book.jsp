<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head><title>Add/Edit Book</title></head>
<body>
    <h1>Add/Edit Book</h1>
    <a href="/books">Back to Books</a>
    <c:if test="${not empty errorMessage}">
        <p style="color:red">${errorMessage}</p>
    </c:if>
    <form:form action="/add-book" modelAttribute="book" method="post">
        <form:hidden path="id"/>
        <p>Title: <form:input path="title"/> <form:errors path="title" cssStyle="color:red"/></p>
        <p>Genre: <form:input path="genre"/> <form:errors path="genre" cssStyle="color:red"/></p>
        <p>ISBN: <form:input path="isbn"/> <form:errors path="isbn" cssStyle="color:red"/></p>
        <p>Author:
            <form:select path="author.id">
                <form:options items="${authors}" itemValue="id" itemLabel="name"/>
            </form:select>
        </p>
        <p><input type="submit" value="Save"/></p>
    </form:form>
</body>
</html>
