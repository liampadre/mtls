FROM alpine:3.11

ENV NGINX_VERSION 1.17.7
ENV HEADERS_MORE=0.33

RUN apk add --update --no-cache curl gnupg pcre-dev gettext openssl openssl-dev ca-certificates sqlite-libs \
    && apk add --no-cache --virtual .build-deps libcap gcc libc-dev make linux-headers zlib-dev \
    && cd /tmp \
    && curl -L https://github.com/openresty/headers-more-nginx-module/archive/v"$HEADERS_MORE".tar.gz | tar -xz \
    && curl -k http://nginx.org/download/nginx-${NGINX_VERSION}.tar.gz  -o nginx-${NGINX_VERSION}.tar.gz \
    && tar xzf nginx-${NGINX_VERSION}.tar.gz \
    && cd /tmp/nginx-${NGINX_VERSION} \
    && ./configure \
        --prefix=/etc/nginx \
        --sbin-path=/usr/sbin/nginx \
        --conf-path=/etc/nginx/nginx.conf \
        --error-log-path=/dev/stderr \
        --http-log-path=/dev/stdout \
        --pid-path=/var/run/nginx.pid \
        --lock-path=/var/run/nginx.lock \
        --http-client-body-temp-path=/var/cache/nginx/client_temp \
        --http-proxy-temp-path=/var/cache/nginx/proxy_temp \
        --http-fastcgi-temp-path=/var/cache/nginx/fastcgi_temp \
        --http-uwsgi-temp-path=/var/cache/nginx/uwsgi_temp \
        --http-scgi-temp-path=/var/cache/nginx/scgi_temp \
        --user=nginx \
        --group=nginx \
        --with-http_ssl_module \
        --with-http_realip_module \
        --with-http_addition_module \
        --with-http_sub_module \
        --with-http_dav_module \
        --with-http_flv_module \
        --with-http_mp4_module \
        --with-http_gunzip_module \
        --with-http_gzip_static_module \
        --with-http_random_index_module \
        --with-http_secure_link_module \
        --with-http_stub_status_module \
        --with-http_auth_request_module \
        --with-threads \
        --with-stream \
        --with-stream_ssl_module \
        --with-http_slice_module \
        --with-mail \
        --with-mail_ssl_module \
        --with-file-aio \
        --with-http_v2_module \
        --with-ipv6 \
        --with-stream_realip_module \
        --add-module=/tmp/headers-more-nginx-module-"$HEADERS_MORE" \
    && make -j$(getconf _NPROCESSORS_ONLN) \
    && make install \
    && adduser -D nginx \
    && mkdir -p /var/cache/nginx \
    && apk del .build-deps \
    && nginx -V \
    && rm -rf /tmp/nginx-${NGINX_VERSION}*

COPY nginx.conf /etc/nginx/nginx.conf
COPY ./ssl/ /etc/nginx/ssl/
COPY error.html /etc/nginx/error.html
COPY error.json /etc/nginx/error.json

RUN mkdir /etc/nginx/logs \
 && mkdir /var/log/nginx \
 && chmod ugo+rwx /tmp \
 && chmod ugo+rwx -R /var/log/nginx \
 && chown -R nginx:nginx /etc/nginx /var/cache/nginx /var/run /run

 CMD nginx -g "daemon off;"
