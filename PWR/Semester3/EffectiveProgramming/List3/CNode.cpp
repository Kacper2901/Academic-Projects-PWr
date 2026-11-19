#include "Constants.h"
#include "CNode.h"
#include <sstream>
#include <cmath>

CNode::CNode() : EType(UNDEFINED), value(EMPTY_STRING),
                 expectedChildrenCount(0), children(NULL),
                 actualChildrenCount(0)
{
}

CNode::CNode(ETokenType EType, std::string &value)
    : EType(EType), value(value),
      expectedChildrenCount(getExpectedChildrenCount()),
      children(new CNode *[expectedChildrenCount]),
      actualChildrenCount(0)
{
    for (int i = 0; i < expectedChildrenCount; i++)
    {
        children[i] = NULL;
    }
}

CNode::~CNode()
{
    clearChildren();
}

CNode::CNode(const CNode &other)
    : EType(other.EType),
      value(other.value),
      expectedChildrenCount(other.expectedChildrenCount),
      children(new CNode *[expectedChildrenCount]),
      actualChildrenCount(0)
{
    for (int i = 0; i < expectedChildrenCount; i++)
    {
        children[i] = NULL;
    }

    for (int i = 0; i < other.actualChildrenCount; i++)
    {
        if (other.children[i] != NULL)
        {
            children[i] = new CNode(*other.children[i]);
        }

        actualChildrenCount++;
    }
}

int CNode::getExpectedChildrenCount()
{
    switch (EType)
    {
    case NUMBER:
        return 0;
    case OPERATOR:
        return 2;
    case VARIABLE:
        return 0;
    case TRIG_FUNCTION:
        return 1;
    default:
        return 0;
    }
}

bool CNode::isNumber(const std::string &token)
{
    if (token.empty())
        return false;

    int length = int(token.length());

    for (int i = 0; i < length; i++)
    {
        if (!isDigit(token[i]))
            return false;
    }

    return true;
}

bool CNode::isVariable(const std::string &token)
{
    if (token.empty())
        return false;

    bool hasLetter = false;
    int length = int(token.length());

    for (int i = 0; i < length; i++)
    {
        if (!isDigitOrLetter(token[i]))
            return false;

        if (isLetter(token[i]))
            hasLetter = true;
    }

    return hasLetter;
}

bool CNode::isOperator(const std::string &token)
{
    return token == "+" || token == "-" || token == "*" || token == "/";
}

bool CNode::isTrigFunction(const std::string &token)
{
    return token == "sin" || token == "cos";
}

bool CNode::isDigit(char c)
{
    return c >= '0' && c <= '9';
}

bool CNode::isLetter(char c)
{
    return (c >= 'a' && c <= 'z') ||
           (c >= 'A' && c <= 'Z');
}

bool CNode::isDigitOrLetter(char c)
{
    return isDigit(c) || isLetter(c);
}

std::string CNode::getNextToken(const std::string &formula, int &formulaIdx)
{
    int length = int(formula.length());

    // skip white spaces befoer
    while (formulaIdx < length && (formula[formulaIdx] == ' ' || formula[formulaIdx] == '\t'))
    {
        formulaIdx++;
    }

    int start = formulaIdx;

    // go until the end of token (" " or enter)
    while (formulaIdx < length && formula[formulaIdx] != ' ' && formula[formulaIdx] != '\t')
    {
        formulaIdx++;
    }

    return formula.substr(start, formulaIdx - start);
}

void CNode::buildFromFormula(const std::string &formula, int &formulaIdx,
                             bool &wasCharacterIgnored, bool &wasArgumentAdded,
                             bool &wasInvalidTokenIgnored)
{
    clearChildren();

    std::string token = getNextToken(formula, formulaIdx);
    std::string cleanedToken = clearToken(token, wasCharacterIgnored);

    while (!token.empty() && cleanedToken.empty())
    {
        token = getNextToken(formula, formulaIdx);
        cleanedToken = clearToken(token, wasCharacterIgnored);
    }

    token = cleanedToken;

    if (token.empty())
    {
        wasArgumentAdded = true;
        EType = NUMBER;
        value = "1";
        expectedChildrenCount = 0;
        actualChildrenCount = 0;
        children = NULL;
        return;
    }

    if (isOperator(token))
    {
        EType = OPERATOR;
        value = token;
    }
    else if (isTrigFunction(token))
    {
        EType = TRIG_FUNCTION;
        value = token;
    }
    else if (isNumber(token))
    {
        EType = NUMBER;
        value = token;
    }
    else if (isVariable(token))
    {
        EType = VARIABLE;
        value = token;
    }
    else
    {
        wasInvalidTokenIgnored = true;
        buildFromFormula(formula, formulaIdx, wasCharacterIgnored,
                         wasArgumentAdded, wasInvalidTokenIgnored);
        return;
    }
    expectedChildrenCount = getExpectedChildrenCount();
    actualChildrenCount = 0;

    if (expectedChildrenCount > 0)
    {
        children = new CNode *[expectedChildrenCount];
        for (int i = 0; i < expectedChildrenCount; i++)
        {
            children[i] = new CNode();
            actualChildrenCount++;
            children[i]->buildFromFormula(formula, formulaIdx, wasCharacterIgnored,
                                          wasArgumentAdded, wasInvalidTokenIgnored);
        }
    }
    else
    {
        children = NULL;
    }
}

void CNode::clearChildren()
{
    if (children != NULL)
    {
        for (int i = 0; i < expectedChildrenCount; i++)
        {
            if (children[i] != NULL)
                delete children[i];
        }

        delete[] children;
    }

    children = NULL;
    expectedChildrenCount = 0;
    actualChildrenCount = 0;
}

std::string CNode::clearToken(const std::string &token, bool &wasCharacterIgnored)
{
    std::string cleanedToken = "";

    for (int i = 0; i < int(token.length()); i++)
    {
        char c = token[i];

        if (isDigitOrLetter(c) ||
            c == '+' || c == '-' ||
            c == '*' || c == '/')
        {
            cleanedToken += c;
        }
        else
        {
            wasCharacterIgnored = true;
        }
    }

    return cleanedToken;
}

std::string CNode::getPrefix() const
{
    std::stringstream res;

    res << this->value;

    for (int i = 0; i < actualChildrenCount; i++)
    {
        if (children[i] != NULL)
            res << " " << children[i]->getPrefix();
    }

    return res.str();
}

void CNode::collectVariables(std::string **vars, int &count, int &capacity)
{
    if (EType == VARIABLE)
    {
        bool exists = false;
        int i = 0;
        while (i < count && exists == false)
        {
            if ((*vars)[i] == this->value)
            {
                exists = true;
            }
            i++;
        }

        if (!exists)
        {
            if (count == capacity)
            {
                capacity = capacity == 0 ? 2 : capacity * 2;
                std::string *newVars = new std::string[capacity];
                for (int i = 0; i < count; i++)
                    newVars[i] = (*vars)[i];
                if (*vars != NULL)
                    delete[] *vars;
                *vars = newVars;
            }
            (*vars)[count++] = this->value;
        }
    }

    for (int i = 0; i < actualChildrenCount; i++)
    {
        if (children[i] != NULL)
            children[i]->collectVariables(vars, count, capacity);
    }
}

double CNode::calculate(std::string *vars, double *values, int count, bool &divisionByZero)
{
    if (EType == NUMBER)
    {
        return stringToNumber(value);
    }

    if (EType == VARIABLE)
    {
        for (int i = 0; i < count; i++)
        {
            if (vars[i] == value)
                return values[i];
        }

        return 0;
    }

    if (EType == TRIG_FUNCTION)
    {
        double argument = children[0]->calculate(vars, values, count, divisionByZero);

        if (value == "sin")
            return std::sin(argument);

        if (value == "cos")
            return std::cos(argument);
    }

    if (EType == OPERATOR)
    {
        double left = children[0]->calculate(vars, values, count, divisionByZero);
        double right = children[1]->calculate(vars, values, count, divisionByZero);

        if (value == "+")
            return left + right;

        if (value == "-")
            return left - right;

        if (value == "*")
            return left * right;

        if (value == "/")
        {
            if (right == 0)
            {
                divisionByZero = true;
                return 0;
            }
            return left / right;
        }
    }

    return 0;
}

int CNode::stringToNumber(const std::string &text)
{
    int number = 0;

    for (int i = 0; i < int(text.length()); i++)
    {
        number = number * DECIMAL_BASE + int(text[i] - '0');
    }

    return number;
}

bool CNode::replaceLeaf(const CNode &other)
{
    if (isLeaf()) // root is leaf
    {
        return false;
    }

    if (children[0]->actualChildrenCount == 0)
    {
        CNode *replacement = new CNode(other); // useful if we want to attach a tree to itself

        delete children[0];
        children[0] = replacement;

        return true;
    }

    return children[0]->replaceLeaf(other);
}

bool CNode::isLeaf()
{
    return actualChildrenCount == 0;
}