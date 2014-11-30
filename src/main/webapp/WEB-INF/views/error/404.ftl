<#include "layout/header.ftl">

 <div class="error-page">
    <h2 class="headline text-info"> 404</h2>
    <div class="error-content">
        <h3><i class="fa fa-warning text-yellow"></i>不好,页面丟了!</h3>
        <p>
		           您请求的页面没有找到!
        </p>
        <form class='search-form'>
            <div class='input-group'>
                <input type="text" name="search" class='form-control' placeholder="Search"/>
                <div class="input-group-btn">
                    <button type="submit" name="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
                </div>
            </div><!-- /.input-group -->
        </form>
    </div><!-- /.error-content -->
</div><!-- /.error-page -->

<#include "layout/footer.ftl">