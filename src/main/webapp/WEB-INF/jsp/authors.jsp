<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head><title>Authors</title></head>
<body>
    <h1>Authors</h1>
    <a href="/">Home</a> | <a href="/add-author">Add New Author</a>
    <table border="1">
        <tr><th>ID</th><th>Name</th><th>Nationality</th><th>Actions</th></tr>
        <c:forEach var="author" items="${authors}">
            <tr>
                <td>${author.id}</td>
                <td>${author.name}</td>
                <td>${author.nationality}</td>
                <td><a href="/edit-author/${author.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
