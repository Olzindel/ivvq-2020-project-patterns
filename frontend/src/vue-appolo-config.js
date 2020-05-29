import {createHttpLink} from 'apollo-link-http'
import {setContext} from 'apollo-link-context'
import ApolloClient from 'apollo-client'
import {InMemoryCache} from 'apollo-cache-inmemory'

const httpLink = createHttpLink({
  uri: process.env.VUE_APP_API_HTTP
})

const authLink = setContext((_, {headers}) => {
  // get the authentication token from local storage if it exists
  const token = localStorage.getItem('connection-token')

  // return the headers to the context so httpLink can read them
  return {
    headers: {
      authorization: token || ''
    }
  }
})

export const cache = new InMemoryCache()

export const apolloClient = new ApolloClient({
  link: authLink.concat(httpLink),
  cache: new InMemoryCache(),
  connectToDevTools: true
})
