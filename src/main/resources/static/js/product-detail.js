document.addEventListener("DOMContentLoaded", function () {

    // 商品価格をHTMLのdata属性から取得
    const price =
        Number(document.body.dataset.price);

    const includeTax =
        Number(document.body.dataset.includeTax);


    // HTML要素
    const quantitySelect =
        document.getElementById("quantity");

    const subtotalElement =
        document.getElementById("subtotal");

    const totalElement =
        document.getElementById("total");

    const cartQuantity =
        document.getElementById("cartQuantity");

    const purchaseQuantity =
        document.getElementById("purchaseQuantity");


    // 金額更新
    function updatePrice() {

        const quantity =
            Number(quantitySelect.value);


        const subtotal =
            price * quantity;


        const total =
            includeTax * quantity;


        subtotalElement.textContent =
            subtotal.toLocaleString();


        totalElement.textContent =
            total.toLocaleString();


        // カートに送る数量
        cartQuantity.value =
            quantity;


        // 単品購入に送る数量
        purchaseQuantity.value =
            quantity;
    }


    // 数量変更
    quantitySelect.addEventListener(
        "change",
        updatePrice
    );


    // 初期表示
    updatePrice();

});
