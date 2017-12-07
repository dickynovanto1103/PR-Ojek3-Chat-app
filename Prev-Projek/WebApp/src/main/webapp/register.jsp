<%--
  Created by IntelliJ IDEA.
  User: wennyyustalim
  Date: 03/11/17
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<div class="login">
    <div class="login-card">
        <h1 class="title">Sign Up</h1>
        <form method="POST" action="register">
            <ul class="login-form register-form">
                <li>
                    <label for="full_name">Your Name</label>
                    <span class="validate">
                        <input type="text" id="full_name" name="full_name">
                        <span class="tooltip"></span>
                    </span>
                </li>
                <li>
                    <label for="username">Username</label>
                    <span class="validate">
                        <input type="text" id="username" name="username">
                        <span class="tooltip"></span>
                        <span class="badge"></span>
                    </span>
                </li>
                <li>
                    <label for="email">Email</label>
                    <span class="validate">
                        <input type="text" id="email" name="email">
                        <span class="tooltip"></span>
                        <span class="badge"></span>
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
                    <label for="confirm_password">Confirm Password</label>
                    <span class="validate">
                        <input type="password" id="confirm_password">
                        <span class="tooltip"></span>
                    </span>
                </li>
                <li>
                    <label for="phone_number">Phone Number</label>
                    <span class="validate">
                        <input type="text" id="phone_number" name="phone_number">
                        <span class="tooltip"></span>
                  </span>
                </li>
                <li class="register-field-driver">
                    <input type="checkbox" id="is_driver" name="is_driver">
                    <label for="is_driver">Also sign me up as a driver!</label>
                </li>
                <li>
                    <a class="login-link-register" href="login.jsp">Already have an account?</a>
                    <input type="submit" id="submit" name="submit" value="Register">
                </li>
            </ul>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp" />
