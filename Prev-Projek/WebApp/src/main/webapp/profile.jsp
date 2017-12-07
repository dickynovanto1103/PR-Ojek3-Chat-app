<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />
<jsp:include page="status.jsp" />

<div class="profile-container">
  <div class="profile-title-container">
    <div class="section-title">
      <h1>MY PROFILE</h1>
    </div>
    <div class="edit-button">
      <div><a href="profile_edit">&#10000;</a></div>
    </div>
  </div>

  <div class="profile-content-container">
    <img class="image-circle" src="default.jpg" />
    <h3>${username}</h3>
    <div class="profile-information-container">
      ${user.fullName}
      <div>
        <c:choose>
          <c:when test="${user.driver}">
            Driver&nbsp;|&nbsp;
            <span id="driver-status">&#9734;${user.popularity.rating}</span>
            <span>&nbsp;(${user.popularity.voteCount} votes)</span>
          </c:when>
          <c:otherwise>
            Non-Driver
          </c:otherwise>
        </c:choose>
      </div>
      <div>&#9993; ${user.email}<div>
      <div>&#9743; ${user.phoneNumber}</div>
    </div>
  </div>
  <div class="location-container">
    <div class="location-title-container">
      <div class="sub-section-title">
        <h2>PREFERRED LOCATIONS:</h2>
      </div>
      <div class="edit-button">
        <div><a href="profile_locations">&#10000;</a></div>
      </div>
    </div>
    <div class="list-container">
      <c:forEach var="location" items="${user.locations}" >
      <ul>
        <li>${location.name}</li>
      </ul>
      </c:forEach>
    </div>
  </div>
</div>

<jsp:include page="footer.jsp" />
