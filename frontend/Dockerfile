FROM nginx:stable-alpine
WORKDIR /app
RUN mkdir ./build
ADD ./build ./build
RUN rm /etc/nginx/conf.d/default.conf
COPY ./front.conf /etc/nginx/conf.d
EXPOSE 3126
CMD ["nginx", "-g", "daemon off;"]