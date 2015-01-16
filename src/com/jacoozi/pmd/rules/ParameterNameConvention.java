/**
 * Copyright (c) 2014 CA.  All rights reserved.
 *
 * This software and all information contained therein is confidential and
 * proprietary and shall not be duplicated, used, disclosed or disseminated
 * in any way except as authorized by the applicable license agreement,
 * without the express written permission of CA. All authorized reproductions
 * must be marked with this language.
 *
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
 * PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT
 * WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN
 * NO EVENT WILL CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY
 * LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE,
 * INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR
 * DAMAGE.
 */
package com.jacoozi.pmd.rules;

import java.util.Iterator;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.ast.ASTFormalParameter;
import net.sourceforge.pmd.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.ast.ASTVariableDeclaratorId;

/**
 * DOCUMENT ME!
 */
public class ParameterNameConvention
   extends AbstractRule
{
   private static final String PATTERN = "[p][a-zA-Z]+";

   /**
    * DOCUMENT ME!
    *
    * @param node DOCUMENT ME!
    * @param data DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
   public Object visit(ASTMethodDeclaration node, Object data)
   {
      RuleContext result = (RuleContext)data;
      String rulePattern = (!getStringProperty("rulePattern").equalsIgnoreCase("")) ? getStringProperty("rulePattern") : PATTERN;

      if (node.containsChildOfType(ASTFormalParameter.class))
      {
         Iterator iterator = node.findChildrenOfType(ASTFormalParameter.class).iterator();

         while (iterator.hasNext())
         {
            ASTFormalParameter element = (ASTFormalParameter)iterator.next();
            Iterator decIdIterator = element.findChildrenOfType(ASTVariableDeclaratorId.class).iterator();

            while (decIdIterator.hasNext())
            {
               ASTVariableDeclaratorId decElement = (ASTVariableDeclaratorId)decIdIterator.next();

               if (!decElement.getImage().matches(rulePattern))
               {
//                  result.getReport().addRuleViolation(new RuleViolation(this, node.getBeginLine(),
//                        "Parameter '" + decElement.getImage() + "' should match regular expression pattern '" + rulePattern + "'", result));

                  result.getReport().addRuleViolation(new RuleViolation(this, result, decElement,
                        "Parameter '" + decElement.getImage() + "' should match regular expression pattern '" + rulePattern + "'"));
               }
            }
         }
      }

      return result;
   }
}