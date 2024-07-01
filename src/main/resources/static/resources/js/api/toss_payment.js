function tosspay(){
    if(inputH.value == null || inputH.value == 'none' || inputH.value == ""){
		alert("결제 개월 수를 선택 해 주세요");
		return;
	}
	
	console.log($('#userid'));
	var IMP = window.IMP;
	IMP.init('imp10657444');
	IMP.request_pay({
        pg: "tosspay_v2.tosstest",
        pay_method: "tosspay", // 'tosspay_card', 'tosspay_money' 도 지원됩니다.
        merchant_uid: "orderMonthly02", // 상점에서 관리하는 주문 번호
        name: "최초인증결제",
        buyer_email: "test@portone.io",
        buyer_name: "포트원",
        buyer_tel: "02-1234-1234",
        m_redirect_url: "{모바일에서 결제 완료 후 리디렉션 될 URL}",
        amount: 1004,
        card: {
          useInstallment: false,
        },
        bypass: {
          expiredTime: "2023-12-02 11:00:00", //결제 만료시간
          cashReceiptTradeOption: "CULTURE", //현금영수증 발급타입
        },
      },function(data){
		if(data.success){
			var msg = "결제 완료";
            msg += '고유ID : ' + data.imp_uid;                //아임포트 uid는 실제 결제 시 결제 고유번호를 서버와 비교해서 결제처리하는데 필요없긴함.
            msg += '// 상점 거래ID : ' + data.merchant_uid;
            msg += '// 결제 금액 : ' + data.paid_amount;
            msg += '// 카드 승인번호 : ' + data.apply_num;
            
            $.ajax({
            	type : 'post',
            	url : '/paySuccess',
            	data : {"ID" : data.buyer_email, "amount" : data.paid_amount},
            });
        }else{
        	var msg = "결제 실패"
        	msg += "에러 내용" + rsp.error_msg;
        }
		alert(msg);
		document.location.href="/video/list";
	});
}