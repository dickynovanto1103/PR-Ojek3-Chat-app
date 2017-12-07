<!--Complete Your Order-->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />
<jsp:include page="order_header.jsp" />

<h2>HOW WAS IT?</h2>
<div class="profile-info">
    <img class="image-circle" src="default.jpg"/>
    <div class="full-name">
        <strong>${driver.fullName}</strong>
    </div>
</div>

<!-- memasukkan rating dan comment -->
<form method="POST" name="form_comment_rating" action="order_destination">
    <div class="rating-comment">
        <div class="rating-box">
            <input type="hidden" name="picking_point" value='${pickingPoint}'>
            <input type="hidden" name="destination" value='${destination}'>
            <input type="hidden" name="driver_id" value='${driver.id}'>

            <label for="rating-input">Enter your rating: </label>
            <input class="rating-input" type="number" name="rating" align="center"><br><br>
        </div>
        <div class="comment-box">
            <div class="comment-box-input">
                <input type="type" name="comment" placeholder="Your comment...">
            </div>
            <div class="comment-box-button">
                <input type="submit" value="COMPLETE ORDER">
            </div>
        </div>
    </div>
</form>

<jsp:include page="footer.jsp" />
