<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Navigasi yg ada 1, 2, 3-->
<jsp:include page="status.jsp" />
<h1>MAKE AN ORDER</h1>
<nav class="navtab_order">
    <a class="<?= $data->current_order_tab == 'destination' ? 'selected_order' : '' ?>"><div class="number">1</div><div class="text">Select Destination</div></a>
    <a class="<?= $data->current_order_tab == 'driver' ? 'selected_order' : '' ?>" ><div class="number">2</div><div class="text">Select a Driver</div></a>
    <a class="<?= $data->current_order_tab == 'chat' ? 'selected_order' : '' ?>" ><div class="number">3</div><div class="text">Chat Driver</div></a>
    <a class="<?= $data->current_order_tab == 'complete' ? 'selected_order' : '' ?>"><div class="number">4</div><div class="text">Complete your order</div></a>
</nav>