<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />
<jsp:include page="history_header.jsp" />

<form method="POST">
  <c:forEach items="${transactions}" var="transaction">
    <div class="history">
      <img src="default.jpg">
      <div>
        <input type="submit" name="hide" value="Hide" formaction="history_user?hide_transaction_id=${transaction.id}">
        <p class="date">${transaction.orderDate}</p>
        <p class="title">${transaction.driver.fullName}</p>
        <p>
          <span>${transaction.pickingPoint}</span>
          &rarr;
          <span>${transaction.destination}</span>
        </p>
        <p>You rated: <span class="star">${transaction.rating}</span></p>
        <p>You commented:</p>
        <div class="comment">${transaction.comment}</div>
      </div>
    </div>
  </c:forEach>
</form>

<jsp:include page="footer.jsp" />
