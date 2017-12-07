<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="status">
    <div class="logo">
        <div class="title"><span>Dolor</span>Sit<span>Amet</span></div>
        <div class="sub-title">naik naik ke atas motor</div>
    </div>
    <div class="login-info">
        <div>Hi, <b>${username}&nbsp;!</b></div>
        <div>
            <a href="logout">Logout</a>
        </div>
    </div>

    <nav class="navtab">
        <a id="navtab-order" class="<?= $data->current_tab == 'order' ? 'selected' : '' ?>" href="order_destination">Order</a>
        <a id="navtab-history" class="<?= $data->current_tab == 'history' ? 'selected' : '' ?>" href="history_user">History</a>
        <a id="navtab-profile" class="<?= $data->current_tab == 'profile' ? 'selected' : '' ?>" href="profile">My Profile</a>
    </nav>
</header>