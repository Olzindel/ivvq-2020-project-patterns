const schema = `
type User {
  id: ID!
    username: String
  password: String
  firstName: String!
    lastName: String!
    email: String!
    gender: String
  street:String
  postalCode:String
  city:String
  role:Role!
    orders: [Order!]
}

input UserInput {
  username: String
  password: String
  firstName: String
  lastName: String
  email: String
  gender: String
  street:String
  postalCode:String
  city:String
  role:Role
  orders: [ID!]
}

enum Role {
  USER
  MERCHANT
}

extend type Query {
  users(count: Int!):[User]
  user(userId: ID!):User
  userFromToken(token: String!):User
}

extend type Mutation {
  updateUser(userId:ID!, input:UserInput!):User
  createUser(input:UserInput):User
  deleteUser(userId:ID!):ID
}
type User {
    id: ID!
    username: String
    password: String
    firstName: String!
    lastName: String!
    email: String!
    gender: String
    street:String
    postalCode:String
    city:String
    role:Role!
    orders: [Order!]
}

input UserInput {
    username: String
    password: String
    firstName: String
    lastName: String
    email: String
    gender: String
    street:String
    postalCode:String
    city:String
    role:Role
    orders: [ID!]
}

enum Role {
    USER
    MERCHANT
}

extend type Query {
    users(count: Int!):[User]
    user(userId: ID!):User
    userFromToken(token: String!):User
}

extend type Mutation {
    updateUser(userId:ID!, input:UserInput!):User
    createUser(input:UserInput):User
    deleteUser(userId:ID!):ID
}
type OrderItem {
    id: ID!
    quantity: Int!
    product: Product!
    order: Order!
}

input OrderItemInput {
    quantity:Int
    productId:ID
    orderId:ID
}

extend type Query {
    orderItems(count: Int!):[OrderItem]
    orderItem(orderItemId: ID!):OrderItem
}

extend type Mutation {
    updateOrderItem(orderItemId:ID!,input:OrderItemInput!):OrderItem
    createOrderItem(input:OrderItemInput):OrderItem
    deleteOrderItem(orderItemId:ID!):ID
}
type Order {
    id: ID!
    status: OrderStatus!
    orderItems: [OrderItem!]
    User: User!
}

input OrderInput {
    status:OrderStatus
    orderItemIds:[ID!]
    userId:ID
}

enum OrderStatus {
    BASKET
    PAID
    ABORTED
}

extend type Query {
    orders(count: Int!):[Order]
    order(orderId: ID!):Order
    enoughStock(orderId:ID!):[Product!]
}

extend type Mutation {
    updateOrder(orderId:ID!,input:OrderInput!):Order
    createOrder(input:OrderInput):Order
    deleteOrder(orderId:ID!):ID
}
type ImageLink {
    id: ID!
    imageLink: String!
    product: Product!
}

input ImageLinkInput {
    imageLink:String
    productId:ID
}

extend type Query {
    imageLinks(count: Int!):[ImageLink]
    imageLink(imageLinkId: ID!):ImageLink
}

extend type Mutation {
    updateImageLink(id: ID!, input:ImageLinkInput!):ImageLink
    createImageLink(input:ImageLinkInput):ImageLink
    deleteImageLink(imageLinkId:ID!):ID
}
`
