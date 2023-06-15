let index={
    init: function(){
        $("#btn-save").on("click",()=>{ //function(){} 이거 대신  ()=> this 를 바인딩하기위해서
            this.save();
        });
        $("#btn-delete").on("click",()=>{ //function(){} 이거 대신  ()=> this 를 바인딩하기위해서
                    this.deleteById();
        });
        $("#btn-update").on("click",()=>{ //function(){} 이거 대신  ()=> this 를 바인딩하기위해서
                            this.update();
        });
        $("#btn-reply-save").on("click",()=>{ //function(){} 이거 대신  ()=> this 를 바인딩하기위해서
                                    this.replySave();
        });
    },

    replySave:function(){

       let data ={
        content: $("#reply-content").val(),
        boardId: $("#boardId").val(),
        userId: $("#userId").val(),
       };

       $.ajax({
        //회원 가입 수행 요청
        type:"POST",
        url: "/api/board/"+data.boardId+"/reply",  //"/api/board/"+data.boardId/+"/reply" 이것과 같은 의미이다
        data: JSON.stringify(data), //http body데이터
        contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
        dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다

       }).done(function(resp){  //성공
            alert("댓글 작성이 완료되었습니다.");
            location.href=`/board/${data.boardId}`;
       }).fail(function(error){  //실패
            alert(JSON.stringify(error));
       });
    },
     replyDelete:function(boardId,replyId){
           $.ajax({
            //회원 가입 수행 요청
            type:"DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다
           }).done(function(resp){  //성공
                alert("댓글 삭제 완료되었습니다.");
                location.href=`/board/${boardId}`;
           }).fail(function(error){  //실패
                alert(JSON.stringify(error));
           });
        },
    save:function(){
       // alert('user의save함수호출됨');
       let data ={
        title: $("#title").val(),
        content: $("#content").val()
       };
       $.ajax({
        //회원 가입 수행 요청
        type:"POST",
        url: "/api/board",
        data: JSON.stringify(data), //http body데이터
        contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
        dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다

       }).done(function(resp){  //성공
            alert("글쓰기가 완료되었습니다.");
            location.href="/";
       }).fail(function(error){  //실패
            alert(JSON.stringify(error));
       });  //ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청!!!
    },
    deleteById:function(){
            let id=$("#id").text();
           $.ajax({
            //회원 가입 수행 요청
            type:"DELETE",
            url: "/api/board/"+id,
            dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다

           }).done(function(resp){  //성공
                alert("삭제가 완료되었습니다.");
                location.href="/";
           }).fail(function(error){  //실패
                alert(JSON.stringify(error));
           });  //ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청!!!
        },
        update:function(){
               let id=$("#id").val();

               let data ={
                title: $("#title").val(),
                content: $("#content").val()
               };

               $.ajax({
                //회원 가입 수행 요청
                type:"PUT", //수정이니까 PUT 사용
                url: "/api/board/"+id,
                data: JSON.stringify(data), //http body데이터
                contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
                dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다

               }).done(function(resp){  //성공
                    alert("글수정이 완료되었습니다.");
                    location.href="/";
               }).fail(function(error){  //실패
                    alert(JSON.stringify(error));
               });  //ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청!!!
            },

}
index.init();