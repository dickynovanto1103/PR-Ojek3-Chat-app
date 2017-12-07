<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<jsp:include page="order_header.jsp" />
<form method="POST">
  <input type="hidden" name="picking_point" value='${pickingPoint}'>
  <input type="hidden" name="destination" value='${destination}'>

  <div class="box">
    <h2>PREFERRED DRIVERS: </h2>
    <c:choose>
      <c:when test="${preferredDrivers.size() > 0}">
        <c:forEach items="${preferredDrivers}" var="driver">
          <div class="row">
            <div class="square-image">
              <img src="default.jpg">
            </div>
            <div class="other-driver-profile-content">
              <div class="driver-profile text-center">
                <div class="row">
                    ${driver.fullName}
                  <c:choose>
                    <c:when test="driver.popularity.vote_count != 0">
                      <span class='color-rating'>&#11090</span> ${driver.popularity.rating}
                      (${driver.popularity.vote_count} votes)
                    </c:when>
                    <c:otherwise>
                      <br/>
                      (No review)
                    </c:otherwise>
                  </c:choose>
                  <br/>
                  <br/>
                </div>
              </div>
              <br/> <br/>
              <div class="button-wrapper">
                <input class="choose-driver-button" type="submit" formaction="order_complete?driver_id=${driver.id}" value="I CHOOSE YOU!">
              </div>
              <br>
            </div>
          </div>
  </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <p align='center'>Nothing to display :(</p>
      </c:otherwise>
    </c:choose>
  </div>

  <div class="box">
    <h2>OTHER DRIVERS: </h2>
    <c:choose>
      <c:when test="${otherDrivers.size() > 0}">
        <c:forEach items="${otherDrivers}" var="driver">
          <div class="row">
            <div class="square-image">
              <img src="default.jpg">
            </div>
            <div class="other-driver-profile-content">
              <div class="driver-profile text-center">
                <div class="row">
                  ${driver.fullName}
                  <c:choose>
                    <c:when test="driver.popularity.vote_count != 0">
                      <span class='color-rating'>&#11090</span> ${driver.popularity.rating}
                      (${driver.popularity.vote_count} votes)
                    </c:when>
                    <c:otherwise>
                      <br/>
                      (No review)
                    </c:otherwise>
                  </c:choose>
                  <br/>
                  <br/>
                </div>
              </div>
              <br/> <br/>
              <div class="button-wrapper">
                <input class="choose-driver-button" type="submit" formaction="order_complete?driver_id=${driver.id}" value="I CHOOSE YOU!">
              </div>
              <br>
            </div>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <p align='center'>Nothing to display :(</p>
      </c:otherwise>
    </c:choose>
  </div>
</form>
<jsp:include page="footer.jsp" />
