export const environment = {
  "backend": {
    "uri": "http://localhost:5267/api"
  },
  "keycloak": {
    "url": "http://localhost:8080",
    urlPattern: /^(http:\/\/localhost:5267)(\/.*)?$/i,
    "authority": "http://localhost:8080/",
    "redirectUri": "http://localhost:4200",
    "postLogoutRedirectUri": "http://localhost:4200/logout",
    "realm": "NoteS",
    "clientId": "browser"
  }
}
