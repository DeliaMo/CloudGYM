$(function(){
    $(document.body).on('click','tr[data-href]',function(){
        window.location.href = this.dataset.href;
        console.log(this);
    })
    $('tr[data-href]').on('mouseenter mouseleave',function(){
        $(this).toggleClass('change_bg');
    })
})