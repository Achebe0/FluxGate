# --- Stage 1: Build the React app ---
FROM node:20-alpine as builder

# Set the working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the source code
COPY . .

# Build the static files
RUN npm run build


# --- Stage 2: Serve the static files with Nginx ---
FROM nginx:stable-alpine

# Copy the built static files from the builder stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy the custom Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose the port Nginx runs on
EXPOSE 80

# Command to start Nginx
CMD ["nginx", "-g", "daemon off;"]