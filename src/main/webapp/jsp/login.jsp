<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<%@include file="head.jsp"%>

<body>
<%@include file="header.jsp"%>

<form action="/login" method="post">
    <section class="dashboard-section">
        <div class="container pt-4 pb-4">
            <div class="border-dashed view-height">
                <div class="container w-25">
                    <form class="padding-small text-center">
                        <h1 class="text-color-darker">Logowanie</h1>
                        ${not empty param.msg ? param.msg : ""}
                        <div class="form-group">
                            <input type="text" class="form-control" id="email" name="email" placeholder="podaj adres email">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="password" name="password" placeholder="podaj hasło">
                        </div>
                        <button class="btn btn-color rounded-0" type="submit">Zaloguj</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</form>
<%@include file="footer.jsp"%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
