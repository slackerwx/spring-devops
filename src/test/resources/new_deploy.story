JBehave Story - An increase test

Meta:

Narrative:
Um membro do time citou que o tempo de deploy estava aumentando ao longo das sprints,
mas não sabia precisar qual era essa taxa de crescimento. Como ação da retrospectiva
surgiu a iniciativa de que fosse desenvolvida alguma solução automática e confiável que
pudesse medir o tempo gasto em cada etapa de cada processo de deploy.

Scenario: quando um membro do time insere as informacoes de um novo deploy

Given a componente
And Versao
And Responsavel
And Status
When receba uma requisição HTTP com os parametros
Then devera persistir todos os dados recebidos e o horário em que a chamada foi recebida

