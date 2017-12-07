<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<jsp:include page="order_header.jsp" />
<br>
<div class="outer-box">
    <div class="order-form">
        <form method="POST" name="form_order" action="order_driver">
            <p>Picking Point &emsp;&emsp;&emsp;<input type="text" name="picking_point"></p>
            <p>Destination &emsp;&emsp;&emsp;&ensp;&nbsp;<input type="text" name="destination"></p>
            <p>Preffered Driver &emsp;&ensp;&nbsp;<input type="text" name="preferred_driver" placeholder="(optional)"> </p>
            <div class="next-button"><input type="submit" id="next-button" value="NEXT"></div>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp" />
