$(function(){
    $('.td_video').on('click',function(){
        if ($(this).children().attr("data-id") == 1) {
            $(this).children().attr("data-id", 2)
            $(this).children().css({
                'z-index' : -1,
                'opacity' : 0
            });
        } else {
            $(this).children().attr("data-id", 1);
            $(this).children().css({
                'z-index' : 1,
                'opacity' : 1
            });
            $(this).children().show().trigger('play');
         }

        })

})