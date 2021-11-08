$(function(){
    $('#td_video').on('click',function(){
        if ($('.td_video_src').attr("data-id") == 1) {
            $('.td_video_src').attr("data-id", 2).css({
                'z-index' : -1,
                'opacity' : 0
            });
        } else {
            $('.td_video_src').attr("data-id", 1).css({
                'z-index' : 1,
                'opacity' : 1
            });
            $('.td_video_src').show().trigger('play');
         }
    })


})