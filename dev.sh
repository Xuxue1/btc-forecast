git pull
cd BIT
cnpm install
cnpm run build
rm -rf /data/www/dapp/bit/dist
cp -r dist /data/www/dapp/bit
nginx -s reload