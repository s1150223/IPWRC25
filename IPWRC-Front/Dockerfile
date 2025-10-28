# ---------- Build Stage ----------
FROM node:18-alpine AS builder
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build


# ---------- Serve Stage ----------
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# ❌ Verwijder de standaard Nginx configuratie
RUN rm -f /etc/nginx/conf.d/default.conf

# ✅ Voeg je eigen config toe
COPY nginx.conf /etc/nginx/conf.d/default.conf

# ✅ Kopieer de Angular build
COPY --from=builder /app/dist/ipwrc-front/browser ./

# ✅ Expose poort 80
EXPOSE 80

# ✅ Start Nginx
CMD ["nginx", "-g", "daemon off;"]
