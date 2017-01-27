Abstract Syntax Tree (AST) for the While language

AST ::= Expression | BoolExpression | Statement
Expression ::= Number | Variable | Addition | Multiplication
BoolExpression ::= BoolConstant | Conjunction | Disjunction | Equality | LessThan | Negation
Statement ::= Skip | Assignment | Output | Sequence | If | While

Number ::= int n;
Variable ::= String id;
Addition ::= Expression + Expression
Multiplication ::= Expression * Expression

BoolConstant ::= (tt | ff);
Conjunction ::= BoolExpression && BoolExpression
Disjunction ::= BoolExpression || BoolExpression
Equality ::= BoolExpression = BoolExpression
LessThan ::= BoolExpression < BoolExpression
Negation ::= !BoolExpression

Skip ::= 
Assignment ::= Variable := Expression
Sequence ::= Statement; Statement
If ::= if (BoolExpression); Statement else Statement
While ::= while (BoolExpression); Statement
Output ::= output Expression