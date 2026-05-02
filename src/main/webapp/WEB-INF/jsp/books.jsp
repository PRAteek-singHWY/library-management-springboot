<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head><title>Books</title></head>
<body>
    <h1>Books</h1>
    <a href="/">Home</a> | <a href="/add-book">Add New Book</a>
    <table border="1">
        <tr><th>ID</th><th>Title</th><th>Genre</th><th>ISBN</th><th>Author</th><th>Actions</th></tr>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.genre}</td>
                <td>${book.isbn}</td>
                <td>${book.author.name}</td>
                <td><a href="/edit-book/${book.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
