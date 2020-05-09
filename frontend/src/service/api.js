import gql from 'graphql-tag'

export const GET_ALL_USERS_QUERY = gql`
  query Users ($count: Int!) {
    users(count: $count) {
      fullName
      email
    }
  }
`
