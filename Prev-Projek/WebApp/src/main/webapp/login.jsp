<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />

<div class="login">
  <div class="login-card">
    <h1 class="title">Login</h1>
    <c:if test="${errorMessage != null}">
      <p>${errorMessage}</p>
    </c:if>

    <form method="POST" action="login">
      <ul class="login-form">
        <li>
          <label for="username">Username</label>
          <span class="validate">
            <input type="text" id="username" name="username">
            <span class="tooltip"></span>
          </span>
        </li>
        <li>
          <label for="password">Password</label>
          <span class="validate">
            <input type="password" id="password" name="password">
            <span class="tooltip"></span>
          </span>
        </li>
        <li>
          <a class="login-link-register" href="register.jsp">Don't have an account?</a>
          <input type="submit" id="submit" value="GO!">
        </li>
      </ul>
    </form>
  </div>
</div>

<jsp:include page="footer.jsp" />
