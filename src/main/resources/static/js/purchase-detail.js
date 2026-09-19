/*
 * 選択されている住所を取得
 */
function getSelectedAddress() {

    const selected =
        document.querySelector(
            'input[name="addressChoice"]:checked'
        );

    if (selected.value === "1") {

        const name =
            document.getElementById("name1").value;

        const address =
            document.getElementById("address1").value;

        const apartment =
            document.getElementById("apartment1").value;

        return name
            + " / "
            + address
            + " / "
            + apartment;

    } else {

        const name =
            document.getElementById("name2").value;

        const address =
            document.getElementById("address2").value;

        const apartment =
            document.getElementById("apartment2").value;

        return name
            + " / "
            + address
            + " / "
            + apartment;
    }
}


/*
 * 選択されているカードを取得
 */
function getSelectedCard() {

    const selected =
        document.querySelector(
            'input[name="cardChoice"]:checked'
        );

    if (selected.value === "1") {

        const cardNumber =
            document.getElementById("cardNumber1").value;

        const expiry =
            document.getElementById("cardExpiry1").value;

        return cardNumber
            + " / "
            + expiry;

    } else {

        const cardNumber =
            document.getElementById("cardNumber2").value;

        const expiry =
            document.getElementById("cardExpiry2").value;

        return cardNumber
            + " / "
            + expiry;
    }
}


/*
 * 「確認」を押したとき
 */
document.getElementById("purchaseForm")
    .addEventListener("submit", function() {

        document.getElementById("deliveryAddress").value =
            getSelectedAddress();

        document.getElementById("paymentMethod").value =
            getSelectedCard();

    });