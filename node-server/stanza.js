let express = require('express');
let app = express();
let bodyParser = require('body-parser');

let HttpRequest = require("./lib/httprequest");
let Neb = require('./lib/neb');
let Account = require('./lib/account');
let Transaction = require('./lib/transaction');
let neb = new Neb();
neb.setRequest(new HttpRequest("https://testnet.nebulas.io"));
//neb.setRequest(new HttpRequest("https://mainnet.nebulas.io"));
app.use(bodyParser.text());

let chainID = 1001;
let app_address = "n1oV3XbFfFixRGQjfHgwTGKbr62mrPWgM2j";



app.post('/announcing', function (req, res) {
    console.log(req.body);
    let parm = JSON.parse(req.body);
    let account_message = ""
    let password = "";
    let time = parm.time;
    let close = parm.closePrice;
    let open = parm.openPrice;
    let acc = new Account();
    acc = acc.fromKey(account_message, password, true);
    let callArgs = [time,open,close];
    neb.api.call({
        chainID: chainID,
        from: "n1KeeRJJPMqRJTd2ecihzx2c5sX1zD3kvaH",
        to: app_address,
        value: 0,
        nonce: 0,
        gasPrice: 1000000,
        gasLimit: 2000000,
        contract: {
            function: "notify",
            args: JSON.stringify(callArgs)
        }
    }).then(function (tx) {
        console.log(tx)
        if (tx.execute_err === undefined || tx.execute_err.length === 0) {
            neb.api.getAccountState(acc.getAddressString()).then(function (state) {
                let callFunction = "notify";
                console.log(JSON.stringify(tx));
                tx = new Transaction({
                    chainID: chainID,
                    from: acc,
                    to: app_address,
                    value: 0,
                    nonce: parseInt(state.nonce) + 1,
                    gasPrice: 1000000,
                    gasLimit: 2000000,
                    contract: {
                        function: callFunction,
                        args: JSON.stringify(callArgs)
                    }
                });
                tx.signTransaction();
                neb.api.sendRawTransaction(tx.toProtoString()).then(function (resp) {
                    resp = resp.result || resp;
                    console.log(resp.txhash)
                }).catch(function (err) {
                    console.log(err);
                })
            }).catch(function (err) {
                console.log(err);
            });
        } else {
            console.log(tx.execute_err)
        }
    });
    res.send("OK")
});

let server = app.listen(9003, function () {
    let host = server.address().address
    let port = server.address().port
    console.log("应用实例，访问地址为 http://%s:%s", host, port)

});
