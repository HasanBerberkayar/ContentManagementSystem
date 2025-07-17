# 1. Aşama: Angular uygulamasını build et
FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# 2. Aşama: NGINX ile yayına al
FROM nginx:alpine

# Nginx default conf'u sil
RUN rm -rf /etc/nginx/conf.d/default.conf

# Yeni nginx.conf dosyasını kopyala
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Angular build çıktısını kopyala
COPY --from=build /app/dist/browser/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]