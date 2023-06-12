let index={
    init: function(){
        $("#btn-save").on("click",()=>{ //function(){} 이거 대신  ()=> this 를 바인딩하기위해서
            this.save();
        });
        $("#btn-update").on("click",()=>{ //function(){} 이거 대신  ()=> this 를 바인딩하기위해서
                    this.update();
                });
    },


    save:function(){
       // alert('user의save함수호출됨');
       let data ={
        username: $("#username").val(),
        password: $("#password").val(),
        email: $("#email").val()
       };

       //console.log(data);

//ajax호출시 default가 비동기 호출
//ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요?!
       $.ajax({
        //회원 가입 수행 요청
        type:"POST",
        url: "/auth/joinProc",
        data: JSON.stringify(data), //http body데이터
        contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
        dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다

       }).done(function(resp){  //성공
            alert("회원가입이 완료되었습니다.");
            location.href="/";
       }).fail(function(error){  //실패
            alert("중복된 아이디 입니다");
            location.href("/joinForm");
            //alert(JSON.stringify(error));
       });  //ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청!!!
    },

    update:function(){
           // alert('user의save함수호출됨');
           let data ={
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
           };
           $.ajax({
            //회원 가입 수행 요청
            type:"PUT",
            url: "/user",
            data: JSON.stringify(data), //http body데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
            dataType: "json"    //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면) -> javscript로 변경해준다

           }).done(function(resp){  //성공
                alert("수정이 완료되었습니다.");
                location.href="/";
           }).fail(function(error){  //실패
                alert(JSON.stringify(error));
           });  //ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청!!!
        }
}
index.init();