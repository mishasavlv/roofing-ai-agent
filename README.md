# Roofing Contractor's AI Agent
*Skills Used:* **OpenAI API, AI Agents, Java Spring Boot, DevOps/Cloud Deployment**

In 2024, I created an AI Agent using OpenAI API (chatgpt-4o-mini), a chat platform, and a Java Spring Boot middleware I wrote connecting the two via API calls. This AI Agent can act as a roofing contractor's AI Agent, and automate customer interactions. The AI Agent uses external function calls and RAG (Retrieval-Augmented Generation).

Demo: https://roofing-ai-agent-poc-oe2dj.ondigitalocean.app/roofing_ai_agent_demo.html

### AI Agent
* Uses a vector storage database of PDF product brochures and RAG to answer questions about products and give recommendations.
* Creates job price estimates (generates quotes) for the user by asking questions and forming a JSON from the customer's responses.
    * The JSON is sent to my Java middleware which calls external tools to calculate the price and then return it to the LLM.
* Takes customer info to schedule an appointment, creates a JSON with the info which is sent to an external tool by middleware.
* Agent instructions and parameters like top_p and temperature tune/sandbox behavior and provide a specific workflow.
    * Instructions can also tune things like product recommendations as needed by the contractor.
* Automatically switches to human agent if customer shows intent to speak to one, or if conversation requires one.

### Middleware
* Abstracted/combined a collection OpenAI API calls by creating one endpoint which takes user input and returns LLM output.
* Formats LLM response into a custom JSON
    * AI Agent can return an imageurl field in it's plaintext response. Middleware parses it if it's present and separates it into it's own field in the JSON it sends to the chat platform, to show the customer an image.
    * If AI Agent includes <u>switchIntent = true</u> in it's response (meaning customer wants to speak to a human) then middleware parses it, sends it to the chat platform as a separate field.
* Deployed middleware on a Heroku dyno.
* Added state to stateless Heroku dyno by connecting key-value store (Redis) to middleware, which holds conversation history that is sent to OpenAI with every API call in a conversation.

### Chat Platform
* Created a chat platform flow which makes API calls to middleware with user's input and 
* Reads fields from middleware respones to show images and switch to a human agent as needed.
* <u>Error Response Mechanism</u>: If the LLM experiences an error or timeout, then the conversation will be recovered. A new thread will be started and the conversation history from the key-value store will be used to continue the conversation.