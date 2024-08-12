# Sistema de Gerenciamento de Hospital
## Contexto
O Hospital Multimed deseja desenvolver um sistema para gerenciar seus pacientes, médicos, enfermeiros, consultas, internações, exames, medicações, departamentos e prontuários. O sistema deve ser capaz de armazenar e gerenciar as informações de maneira eficiente, facilitando o acesso e a atualização dos dados.

## Entidades
Todas as entidades terão um identificador único. Para isso, será utilizado UUIDs.

Os dados de um **paciente** incluem nome, data de nascimento, endereço, telefone de contato, e histórico médico. Cada paciente pode ter múltiplas consultas, internações e exames.

Os dados de um **médico** incluem nome, especialidade, número de licença médica e contato. Um médico pode realizar múltiplas consultas e prescrever medicações para diversos pacientes. Médicos pertencem a um ou mais departamentos.

Os dados de um **enfermeiro** incluem nome, setor de atuação, e contato. Enfermeiros auxiliam nas internações e nas triagens.

Os dados de **triagem** incluem o paciente, o enfermeiro que realizou a triagem, a data e hora e para qual consulta ele foi encaminhado (se foi).

Os dados de uma **consulta** incluem o paciente, o médico, a data, a hora, o diagnóstico, e o tratamento. Consultas podem resultar em prescrições de medicações, solicitações de exames ou internação.

Os dados de uma **internação** incluem o paciente, o quarto, a data de entrada, a data de saída (se aplicável), e o motivo da internação. Durante a internação, pacientes podem ser transferidos de quarto e receber diferentes tratamentos e medicações.

Os dados de um **exame** incluem o paciente, o tipo de exame, a data, os resultados e o médico solicitante. Exames podem ser laboratoriais, de imagem, ou outros tipos específicos.

Os dados de um **departamento** incluem nome, localização, e o chefe do departamento, que também é um médico. Departamentos incluem áreas como cardiologia, neurologia, pediatria, etc.

O sistema deve permitir o Cadastro, Leitura, Atualização, Exclusão das devidas entidades, mediante permissões.
