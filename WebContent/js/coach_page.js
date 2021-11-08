$(function(){
    
    let video_length = $("div.video").find("li").length;
    
    if(video_length < 4){
        $("div.video i.left").addClass("-on");
        $("div.video i.right").addClass("-on");
        $("div.video li").eq(2).addClass("last");
    }else if(video_length < 5){
        $("div.video i.left").addClass("-on");
        $("div.video i.right").addClass("-on");
        $("div.video li").eq(3).addClass("last");
    }
    
    



    $("div.menu li").slice(3).addClass("-on");
    $("div.video li").slice(4).addClass("-on");
    var undisplay = 0;

    if(!$("div.menu li").eq(0).hasClass("-on")){
        $("div.menu i.left").addClass("-on");
    }
    if(!$("div.video li").eq(0).hasClass("-on")){
        $("div.video i.left").addClass("-on");
    }

    $("div.menu li").eq(undisplay+2).addClass("last");
    $("div.video li").eq(undisplay+3).addClass("last");

    $("div.menu i.left").on("click", function(){
        console.log("click left");
        $("div.menu li").eq(undisplay-1).toggleClass("-on");
        $("div.menu li").eq(undisplay+2).toggleClass("-on").removeClass("last");
        $("div.menu li").eq(undisplay+1).addClass("last");
        undisplay--;
        if(!$("div.menu li").last().hasClass("last")){
            $("div.menu i.right").removeClass("-on");
        }
        if(!$("div.menu li").first().hasClass("-on")){
            $("div.menu i.left").addClass("-on");
        }
        
    })

    $("div.menu i.right").on("click", function(){
        console.log("click right");
        $("div.menu li").eq(undisplay).toggleClass("-on");
        $("div.menu li").eq(undisplay+3).toggleClass("-on").addClass("last");
        $("div.menu li").eq(undisplay+2).removeClass("last");
        undisplay++;
        if($("div.menu li").last().hasClass("last")){
            $("div.menu i.right").addClass("-on");
        }
        if($("div.menu li").first().hasClass("-on")){
            $("div.menu i.left").removeClass("-on");
        }
        if(!$("div.menu li").first().hasClass("-on")){
            $("div.menu i.left").addClass("-on");
        }
    })

/*******************************************************************************/

    $("div.video i.left").on("click", function(){
        console.log("click left");
        $("div.video li").eq(undisplay-1).toggleClass("-on");
        $("div.video li").eq(undisplay+3).toggleClass("-on").removeClass("last");
        $("div.video li").eq(undisplay+2).addClass("last");
        undisplay--;
        if(!$("div.video li").last().hasClass("last")){
            $("div.video i.right").removeClass("-on");
        }
        if(!$("div.video li").first().hasClass("-on")){
            $("div.video i.left").addClass("-on");
        }
        
    })

    $("div.video i.right").on("click", function(){
        console.log("click right");
        $("div.video li").eq(undisplay).toggleClass("-on");
        $("div.video li").eq(undisplay+4).toggleClass("-on").addClass("last");
        $("div.video li").eq(undisplay+3).removeClass("last");
        undisplay++;
        if($("div.video li").last().hasClass("last")){
            $("div.video i.right").addClass("-on");
        }
        if($("div.video li").first().hasClass("-on")){
            $("div.video i.left").removeClass("-on");
        }
        if(!$("div.video li").first().hasClass("-on")){
            $("div.video i.left").addClass("-on");
        }
    })
    
    $("i.bi-cart-fill span").attr("style", "");
});