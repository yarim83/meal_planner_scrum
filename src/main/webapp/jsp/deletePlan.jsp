<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<%@include file="head.jsp"%>

<body>
<%@include file="header2.jsp"%>
<br>
<h3 align="center">Czy na pewno chcesz usunac plan: ${plan.name}?</h3>
<form action="/deletePlan" method="post">
    <div align="center"><input type="submit" value="OK" name="confirm" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">
        <input type="submit" value="Anuluj" name="decline" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4"></div>
    <input type="hidden" name="planId" value="${plan.id}">
</form>

</body>
</html>