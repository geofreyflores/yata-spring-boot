FROM node:12-alpine3.9 AS NODE_BUILD

# install dependencies in a separate layer
WORKDIR /app
COPY package.json .
RUN npm i

# then copy and build source
COPY . .
RUN npm run build

FROM node:12-alpine3.9 
WORKDIR /app
RUN npm i -g serve
COPY --from=NODE_BUILD /app/build .
CMD ["serve", "-p", "80", "-s", "."]
